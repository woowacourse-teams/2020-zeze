import React from 'react';
import styled from "@emotion/styled";
import {Toast} from "../../domains/constants";

const ToastNotificationBlock = styled.div`
  .notification-container {
    font-size: 14px;
    box-sizing: border-box;
    position: fixed;
    z-index: 999999;
  }
  
  .top-right {
    top: 12px;
    right: 12px;
    transition: transform .6s ease-in-out;
    animation: toast-in-right .7s;
  }

  .bottom-right {
    bottom: 12px;
    right: 12px;
    transition: transform .6s ease-in-out;
    animation: toast-in-right .7s;
  }

  .top-left {
    top: 12px;
    left: 12px;
    transition: transform .6s ease-in;
    animation: toast-in-left .7s;
  }

  .bottom-left {
    bottom: 12px;
    left: 12px;
    transition: transform .6s ease-in;
    animation: toast-in-left .7s;
  }
  
  .notification {
    background: #fff;
    transition: .3s ease;
    position: relative;
    pointer-events: auto;
    overflow: hidden;
    margin: 0 0 6px;
    padding: 30px;
    margin-bottom: 15px;
    width: 300px;
    max-height: 100px;
    border-radius: 3px 3px 3px 3px;
    color: #000;
    opacity: .9;
    background-position: 15px;
    background-repeat: no-repeat;
  }

  .notification:hover {
    opacity: 1;
  }

  .notification-title {
    font-weight: 700;
    font-size: 16px;
    text-align: left;
    margin-top: 0;
    margin-bottom: 6px;
    width: 300px;
    height: 18px;
  }

  .notification-message {
    margin: 0;
    text-align: left;
    height: 18px;
    margin-left: -1px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  .notification-image {
    float: left;
    margin-right: 15px;
  }

  .notification-image img {
    width: 30px;
    height: 30px;
  }

  .toast {
    height: 50px;
    width: 365px;
    color: #fff;
    padding: 20px 15px 10px 10px;
  }

  .notification-container button {
    position: relative;
    right: -.3em;
    top: -.3em;
    float: right;
    font-weight: 700;
    color: #fff;
    outline: none;
    border: none;
    text-shadow: 0 1px 0 #fff;
    opacity: .8;
    line-height: 1;
    font-size: 16px;
    padding: 0;
    cursor: pointer;
    background: 0 0;
    border: 0
  }
  
  .notification button:hover {
    opacity: 1;
    cursor: pointer;
  }

  @keyframes toast-in-right {
    from {
      transform: translateX(100%);
      
    }
    to {
      transform: translateX(0);
    }
}

@keyframes toast-in-left {
  from {
    transform: translateX(-100%);
  }
  to {
    transform: translateX(0);
  }
}
`

interface IProps {
  toasts: Array<Toast>,
  position: string
  deleteToast(id: number): void,
}

const ToastAlarm: React.FC<IProps> = ({toasts, position, deleteToast}) => {
  return (
    <ToastNotificationBlock>
      <div className={`notification-container ${position}`}>
        {
          toasts.map((toast, i) =>
            <div key={i} className={`notification toast ${position}`} style={{backgroundColor: toast.backgroundColor}}>
              <button onClick={() => deleteToast(toast.id)}>
                X
              </button>
              <div className={"notification-image"}>
                <img src={toast.icon} alt={""}/>
              </div>
              <div>
                <p className={"notification-title"}>{toast.title}</p>
              </div>
            </div>
          )
        }
      </div>
    </ToastNotificationBlock>
  );
};

export default ToastAlarm;
