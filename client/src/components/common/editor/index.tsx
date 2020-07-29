import React, {useEffect, useRef} from "react";
import styled from "@emotion/styled";

import CodeMirror from "codemirror";
import "codemirror/lib/codemirror.css";
import "codemirror/theme/darcula.css";
import "codemirror/mode/markdown/markdown";
import {css, Global} from "@emotion/core";

interface IProps {
  defaultValue?: string;
  onChange?: (newValue: string) => void;
  onDrop?: (files: File) => Promise<string>;
}

const StyledTextArea = styled.textarea`
  display: none;
`;

const Editor: React.FC<IProps> = ({defaultValue = "", onChange, onDrop}) => {
  const textareaRef = useRef<HTMLTextAreaElement>(null);

  useEffect(() => {
    const codemirror = CodeMirror.fromTextArea(textareaRef.current!, {
      lineNumbers: true,
      mode: "text/markdown",
      theme: "darcula",
    });

    codemirror.on("change", editor => {
      onChange?.(editor.getValue());
    });

    codemirror.on("drop", async (editor, e) => {
      const fileList = e.dataTransfer?.files;

      if (!fileList) {
        return;
      }

      const files: File[] = Array.prototype.slice.call(fileList, 0, fileList.length);

      files.filter(({type}) => type.split("/")[0] === "image")
        .forEach(async file => {
          const marker = `![Uploading ${file.name}...]()`;

          editor.replaceRange(`${marker}\n`, editor.getCursor());
          const uploadUrl = await onDrop?.(file);
          const cursor = editor.getCursor();

          editor.setValue(editor.getValue().replace(marker, `![${file.name}](${uploadUrl})`));
          editor.setCursor(cursor);
        });
    });

    codemirror.setSize("100%", "100%");
    codemirror.setValue(defaultValue);

    return () => {
      codemirror.toTextArea();
    };
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <>
      <Global styles={css`
        .cm-s-darcula.CodeMirror {
          font-family: "D2 coding", Consolas, Aria, Menlo, Monaco, 'Lucida Console', 'Liberation Mono', 'DejaVu Sans Mono', 'Bitstream Vera Sans Mono', 'Courier New', monospace, serif;
        }
      `}/>
      <StyledTextArea ref={textareaRef}/>
    </>
  );
};

export default Editor;
