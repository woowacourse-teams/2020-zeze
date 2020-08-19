import React, {useEffect} from 'react';
import styled from "@emotion/styled";
import {MOBILE_MAX_WIDTH, Toast} from "../../domains/constants";
import {useRecoilState} from "recoil";
import {toastMessages} from "../../store/atoms";

const autoDeleteTime = 3000;

const ToastNotificationBlock = styled.div`
  position: relative;
  
  .notification-container {
    font-size: 14px;
    box-sizing: border-box;
    position: fixed;
    z-index: 999999;
    @media (max-width: ${MOBILE_MAX_WIDTH}px) {
      padding-left: 0;
    }
  }
  
  .top-center {
    top: 2%;
    left: 32%;
    transition: transform .6s ease-in;
    animation: toast-from-top ${autoDeleteTime + 500}ms;
  }
  
  .notification {
    background: #fff;
    transition: .3s ease;
    position: relative;
    pointer-events: auto;
    overflow: hidden;
    margin: 0 0 6px;
    padding: 30px;
    margin-bottom: 1rem;
    max-height: 100px;
    border-radius: 3px 3px 3px 3px;
    color: #000;
    opacity: .9;
    background-position: 15px;
    background-repeat: no-repeat;
    border-radius: 20px;
    display: flex;
  }

  .notification:hover {
    opacity: 1;
  }
  
  .notification-center {
    display: flex;
    align-items: center;
    align-content: center;
    align-self: center;
  }

  .notification-title {
    font-weight: 700;
    font-size: 16px;
    text-align: center;
    margin-top: 0;
    margin-bottom: 0.7rem;
    width: 275px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    height: 18px;
  }

  .notification-image {
    margin-right: 1rem;
    margin-bottom: 0.5rem;
  }

  .notification-image img {
    width: 15px;
    height: 15px;
  }

  .toast {
    height: 20px;
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
  
  @keyframes toast-from-top {
    0% {
      transform: translateY(-100%);
    }
    30% {
      transform: translateY(0);
    }
    70% {
      transform: translateY(0);
    }
    100% {
      transform: translateY(-130%);
    }
  }
`

const ToastAlarm: React.FC = () => {
  const [toasts, setToasts] = useRecoilState<Array<Toast>>(toastMessages);
  const removeToast = (toast : Toast) => {
    const newToasts = toasts.filter(value => value !== toast)
    setToasts([...newToasts]);
  };
  useEffect(() => {
    const interval = setInterval(() => {
      if (toasts.length) {
        removeToast(toasts[0]);
      }
    }, autoDeleteTime);
    return () => {
      clearInterval(interval);
    }
  }, [toasts, removeToast]);
  return (
    <ToastNotificationBlock>
      <div className={`notification-container top-center`}>
        {
          toasts.map((toast, i) =>
            <div key={i} className={`notification toast top-center`} style={{backgroundColor: toast.backgroundColor}}>
              <div className={"notification-image notification-center"}>
                <img src={toast.icon} alt={""}/>
              </div>
              <div className={"notification-center"}>
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
