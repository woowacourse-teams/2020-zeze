import {useCallback} from "react";
import {useRecoilState} from "recoil";
import {Toast, ToastType} from "./constants";
import {toastMessages} from "../store/atoms";

export interface IToastFactory {
  createToast(title: string, type: ToastType) : void,
}

export const ToastFactory = (): IToastFactory => {
  const generateBackgroundColor = useCallback<(type: ToastType) => string>((type: ToastType) => {
    switch (type) {
    case ToastType.SUCCESS:
      return "#5cb85c";
    case ToastType.INFO:
      return "#5bc0de";
    case ToastType.WARN:
      return "#f0ad4e";
    default:
      return "#d9534f";
    }
  }, []);
  const generateIcon = useCallback<(type: ToastType) => string>((type: ToastType) => {
    switch (type) {
    case ToastType.SUCCESS:
      return "/assets/icons/toast_check.svg";
    case ToastType.INFO:
      return "/assets/icons/toast_info.svg";
    case ToastType.WARN:
      return "/assets/icons/toast_warn.svg";
    default:
      return "/assets/icons/toast_error.svg";
    }
  }, []);

  const [toasts, setToasts] = useRecoilState<Array<Toast>>(toastMessages);

  const addToast = (toast: Toast) => {
    setToasts([...toasts, toast]);
  };

  const createToast = (title:string, type: ToastType) => {
    const toast = {
      title,
      backgroundColor: generateBackgroundColor(type),
      icon: generateIcon(type),
    };

    addToast(toast);
  };

  return {
    createToast,
  };
};

export default ToastFactory;
