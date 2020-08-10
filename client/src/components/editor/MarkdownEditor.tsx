import React, { useEffect, useRef, useState } from "react";
import styled from "@emotion/styled";

import CodeMirror from "codemirror";
import "codemirror/lib/codemirror.css";
import "codemirror/theme/darcula.css";
import "codemirror/mode/markdown/markdown";
import { css, Global } from "@emotion/core";

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
}
const MarkdownEditor: React.FC<IProps> = ({ inputRef, onChange, onDrop }) => {
  const textareaRef = useRef<HTMLTextAreaElement>(null);

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
        .forEach(file => {
          const setEditor = async () => {
            const marker = `![Uploading ${file.name}...]()`;

            editor.replaceRange(`${marker}\n`, editor.getCursor());
            const uploadUrl = await onDrop?.(file);
            const cursor = editor.getCursor();

            editor.setValue(editor.getValue().replace(marker, `![${file.name}](${uploadUrl})`));
            editor.setCursor(cursor);
          };

          setEditor();
        });
    });

    codemirror.setSize("100%", "100%");

    inputRef.current = codemirror;

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
