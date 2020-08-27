import {useRef, useState, useEffect, useCallback} from "react";

type SlideRemoteControl = [number, () => void, () => void];

enum RemoteEvent {
  PREV = 37,
  NEXT = 39
}

const PREV_PACKET = new Uint8Array(1);
PREV_PACKET[0] = RemoteEvent.PREV;

const NEXT_PACKET = new Uint8Array(1);
NEXT_PACKET[0] = RemoteEvent.NEXT;

const REMOTE_API_URL = `ws://${window.location.hostname}:8080/remote`;

export const useSlideRemoteControl = (token: string, slide: number): SlideRemoteControl => {
  const socketRef = useRef<WebSocket>();
  const [input, setInput] = useState<number>(0);

  useEffect(() => {
    const socket = new WebSocket(`${REMOTE_API_URL}?token=${token}&slide=${slide}`)
    socket.binaryType = "arraybuffer";
    socket.onmessage = (e) => {
      const buffer = new Uint8Array(e.data);
      switch (buffer[0]) {
        case RemoteEvent.PREV:
          setInput(-1);
          break;
        case RemoteEvent.NEXT:
          setInput(1);
          break;
      }
    };
    socketRef.current = socket;

    return () => {
      socket.close();
    };
  }, [token, slide]);

  useEffect(() => {
    if (input) {
      setInput(0);
    }
  }, [input]);

  const prev = useCallback(() => {
    socketRef.current?.send(PREV_PACKET);
  }, []);

  const next = useCallback(() => {
    socketRef.current?.send(NEXT_PACKET);
  }, []);

  return [
    input,
    prev,
    next
  ];
};
