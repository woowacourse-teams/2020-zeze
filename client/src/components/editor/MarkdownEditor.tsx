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
  value?: string;
  onChange?: (newValue: string) => void;
  onDrop?: (files: File) => Promise<string>;
}
const MarkdownEditor: React.FC<IProps> = ({ value = "", onChange, onDrop }) => {
  const [codemirror, setCodemirror] = useState<CodeMirror.Editor | null>(null);
  const textareaRef = useRef<HTMLTextAreaElement>(null);

  useEffect(() => {
    const editorFromTextArea = CodeMirror.fromTextArea(textareaRef.current!, {
      lineNumbers: true,
      mode: "text/markdown",
      theme: "darcula",
    });

    editorFromTextArea.on("change", editor => {
      onChange && onChange(editor.getValue());
    });

    editorFromTextArea.on("drop", async (editor, e) => {
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

    editorFromTextArea.setSize("100%", "100%");
    editorFromTextArea.setValue(value);

    setCodemirror(editorFromTextArea);

    return () => {
      editorFromTextArea.toTextArea();
    };
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  useEffect(() => {
    if (!codemirror) return;
    const cursor = codemirror.getCursor();

    codemirror.setValue(value);
    codemirror.setCursor(cursor);
  }, [codemirror, value]);

  return (
    <>
      <Global styles={codeMirrorStyle} />
      <StyledTextArea ref={textareaRef} />
    </>
  );
};

export default MarkdownEditor;
