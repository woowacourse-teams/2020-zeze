import React, {useEffect, useRef, useState} from "react";
import styled from "@emotion/styled";

import CodeMirror from "codemirror";
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
  onDrop?: (files: File) => Promise<string>;
  onExternalDrop?: (url: string, name: string) => Promise<string>;
  onSaveKeyDown: () => void;
}

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
      const fileList = e.dataTransfer?.files;

      if (!fileList) {
        return;
      }

      const files: File[] = Array.prototype.slice.call(fileList, 0, fileList.length);

      files.filter(({ type }) => type.split("/")[0] === "image")
        .forEach(async file => {
          const marker = `![Uploading ${file.name}...]()`;
          editor.replaceRange(`${marker}\n`, editor.getCursor());

          const uploadUrl = await onDrop?.(file);

          const cursor = editor.getCursor();
          const scroll = editor.getScrollInfo();
          editor.setValue(editor.getValue().replace(marker, `![${file.name}](${uploadUrl})`));
          editor.setCursor(cursor);
          editor.scrollTo(scroll.left, scroll.top);
        });
    });

    codemirror.on("drop", async (editor, e) => {
      const template = document.createElement("div");
      template.innerHTML = e.dataTransfer?.getData("text/html") ?? "";
      const image = template.querySelector("img");

      if (image) {
        const marker = `![Uploading ${image.alt}...]()`;
        editor.replaceRange(`${marker}\n`, editor.getCursor());

        const uploadUrl = await onExternalDrop?.(image.src, "external");

        const cursor = editor.getCursor();
        const scroll = editor.getScrollInfo();
        editor.setValue(editor.getValue().replace(marker, `![${image.alt}](${uploadUrl})`));
        editor.setCursor(cursor);
        editor.scrollTo(scroll.left, scroll.top);
      }
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
      <Global styles={codeMirrorStyle} />
      <StyledTextArea ref={textareaRef} />
    </>
  );
};

export default MarkdownEditor;
