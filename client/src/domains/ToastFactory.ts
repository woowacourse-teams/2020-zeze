import {useCallback} from "react";
import {useRecoilState} from "recoil";
import {Toast, ToastType} from "./constants";
import {toastMessages} from "../store/atoms";
import {checkIcon, errorIcon, infoIcon, warnIcon} from "../assets/icons";

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
      return checkIcon;
    case ToastType.INFO:
      return infoIcon;
    case ToastType.WARN:
      return warnIcon;
    default:
      return errorIcon;
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
