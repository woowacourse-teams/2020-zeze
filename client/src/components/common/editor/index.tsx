import React, { useRef, useEffect } from "react";
import styled from "@emotion/styled";

import CodeMirror from "codemirror";
import "codemirror/lib/codemirror.css";
import "codemirror/theme/darcula.css";
import "codemirror/mode/markdown/markdown";

interface IProps {
  defaultValue?: string;
  onChange?: (newValue: string) => void;
}

const StyledTextArea = styled.textarea`
  display: none;
`;

const Editor: React.FC<IProps> = ({ defaultValue = "", onChange }) => {
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

    codemirror.setSize("100%", "100%");
    codemirror.setValue(defaultValue);

    return () => {
      codemirror.toTextArea();
    };
  }, [defaultValue, onChange]);

  return (
    <StyledTextArea ref={textareaRef} />
  );
};

export default Editor;
