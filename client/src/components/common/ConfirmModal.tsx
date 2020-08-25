import React from "react";
import styled from "@emotion/styled";
import Modal, {IProps as ModalProps} from "./Modal";

const Content = styled.div`
  display: flex;
  flex-direction: column;
  flex: 1;
  justify-content: center;
  align-items: center;
  font-size: 16px;
  color: #555;
  font-weight: bold;
`;

const ButtonGroup = styled.div`
  display: flex;
  justify-self: flex-end;
  align-self: flex-end;
  padding: 5px;
  margin: 0;
`;

const Button = styled.button`
  border: 0;    
  outline: none;
  padding: 5px 10px;
  border-radius: 5px;
  margin: 3px;
  cursor: pointer;
  font-size: 12px;
`;

const SubmitButton = styled(Button)`
  background: #eb4d4b;
  color: #fff;
`;

const CancelButton = styled(Button)`
  background: #95afc0;
  color: #fff;
`;

interface IProps extends ModalProps {
  onSubmit: () => void,
  onCancel: () => void,
}

const ConfirmModal: React.FC<IProps> = ({children, visibility, onBackdropClick, onSubmit, onCancel}) => {
  return (
    <Modal visibility={visibility} onBackdropClick={onBackdropClick}>
      <Content>{children}</Content>
      <ButtonGroup>
        <SubmitButton onClick={onSubmit}>OK</SubmitButton>
        <CancelButton onClick={onCancel}>Cancel</CancelButton>
      </ButtonGroup>
    </Modal>
  );
};

export default ConfirmModal;
