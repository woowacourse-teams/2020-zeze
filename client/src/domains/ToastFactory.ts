import {Toast, ToastType} from "./constants";
import {checkIcon} from "../assets/icons";

export const ToastFactory = () => {
  let id = 1;

  const create = (title:string, description: string, type: ToastType) : Toast => {
    let toast : Toast;
    switch (type) {
      case ToastType.SUCCESS:
        toast =  {
          id,
          title,
          description,
          backgroundColor: '#5cb85c',
          icon: checkIcon,
        }
      default:
        toast =  {
          id,
          title,
          description,
          backgroundColor: '#5cb85c',
          icon: checkIcon,
        }
    }
    id += 1;
    return toast;
  }
};
