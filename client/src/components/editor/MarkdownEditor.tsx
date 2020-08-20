import React, {useEffect, useRef, useState} from "react";
import styled from "@emotion/styled";

import CodeMirror from "codemirror";
import "codemirror/addon/search/searchcursor";
import "codemirror/addon/search/search";
import "codemirror/lib/codemirror.css";
import "codemirror/theme/darcula.css";
import "codemirror/mode/markdown/markdown";
import {css, Global} from "@emotion/core";

const codeMirrorStyle = css`
  .cm-s-darcula.CodeMirror {
    font-family: "D2 coding", Consolas, Aria, Menlo, Monaco, 'Lucida Console', 'Liberation Mono', 'DejaVu Sans Mono', 'Bitstream Vera Sans Mono', 'Courier New', monospace, serif;
  }
`;

const StyledTextArea = styled.textarea`
  display: none;
`;

interface IProps {
  inputRef: React.MutableRefObject<CodeMirror.Editor | null>
  onChange?: (newValue: string) => void;
  onDrop: (files: File) => Promise<string>;
  onExternalDrop: (url: string, name: string) => Promise<string>;
  onSaveKeyDown: () => void;
}

const dataUrlToFile = (dataUrl: string, filename: string): File => {
  const decoded = atob(dataUrl.split(",")[1]);
  const buffer = new Uint8Array(decoded.length);
  for (let i = 0; i < decoded.length; ++i) {
    buffer[i] = decoded.charCodeAt(i);
  }
  return new File([buffer], filename);
};

const MarkdownEditor: React.FC<IProps> = ({inputRef, onChange, onDrop, onExternalDrop, onSaveKeyDown}) => {
  const textareaRef = useRef<HTMLTextAreaElement>(null);
  const [codemirror, setCodeMirror] = useState<CodeMirror.Editor | null>(null);

  useEffect(() => {
    codemirror?.removeKeyMap(navigator.platform.match("Mac") ? "Cmd-S" : "Ctrl-S");
    codemirror?.addKeyMap({
      [navigator.platform.match("Mac") ? "Cmd-S" : "Ctrl-S"]: () => {
        onSaveKeyDown();
      },
    });
  }, [codemirror, onSaveKeyDown]);

  useEffect(() => {
    const codemirror = CodeMirror.fromTextArea(textareaRef.current!, {
      lineNumbers: true,
      mode: "text/markdown",
      theme: "darcula",
    });

    codemirror.on("change", editor => {
      onChange && onChange(editor.getValue());
    });

    codemirror.on("drop", async (editor, e) => {
      const fileList = e.dataTransfer?.files ?? [];
      const files: File[] = Array.prototype.slice.call(fileList, 0, fileList.length);
      const template = document.createElement("div");
      template.innerHTML = e.dataTransfer?.getData("text/html") ?? "";
      const image = template.querySelector("img");

      const drop = async (filename: string, promise: Promise<string>) => {
        const name = filename.replace(/[\[|\]]/g, "");
        const marker = `![Uploading ${name}...]()`;
        editor.replaceRange(`${marker}\n`, editor.getCursor());
        const uploadUrl = await promise;
        const searchCursor = editor.getSearchCursor(marker);
        searchCursor.findNext() && editor.replaceRange(`![${name}](${uploadUrl})`, searchCursor.from(), searchCursor.to());
      };

      if (image) {
        const dataUrl = image.src.match(/^data:image/)?.input;
        if (dataUrl) {
          drop(image.alt, onDrop(dataUrlToFile(dataUrl, "untitled")));
        } else {
          drop(image.alt, onExternalDrop(image.src, "untitled"));
        }
        return;
      }

      files.filter(({ type }) => type.split("/")[0] === "image")
        .forEach(file => drop(file.name, onDrop(file)));
    });

    codemirror.setSize("100%", "100%");

    inputRef.current = codemirror;

    setCodeMirror(codemirror);

    return () => {
      codemirror.toTextArea();
    };
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [onChange, onDrop]);

  return (
    <>
      <Global styles={codeMirrorStyle}/>
      <StyledTextArea ref={textareaRef}/>
    </>
  );
};

export default MarkdownEditor;
