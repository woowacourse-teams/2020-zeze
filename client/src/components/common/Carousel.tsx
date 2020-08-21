import React, {useState, useCallback, useMemo, useEffect} from "react";
import styled from "@emotion/styled";

const CarouselBlock = styled.div`
  position: relative;
  width: 100%;
  height: 100%;
  overflow: hidden;
  background: #fff;
`;

interface IProps {
  cursor: number;
}

const CarouselWarpper = styled.div<IProps>`
  position: absolute;
  display: flex;
  width: 100%;
  height: 100%;
  transition: 0.5s;
  transform: translateX(-${({cursor}) => cursor * 100}%);
  
  > * {
    flex: 0 0 100%;
    height: 100%;
  }
`;

const Button = styled.button`
  position: absolute;
  top: 50%;
  width: 32px;
  height: 32px;
  border: 0;
  outline: none;
`;

const LeftButton = styled(Button)`
  background: url("/assets/icons/left-arrow.svg");
`;

const RightButton = styled(Button)`
  left: calc(100% - 32px);
  background: url("/assets/icons/right-arrow.svg");
`;

const Carousel: React.FC = ({children}) => {
  const count = useMemo(() => React.Children.count(children), [children]);
  const [cursor, setCursor] = useState<number>(0);

  useEffect(() => {
    setCursor(0);
  }, [count]);

  const prev = useCallback(() => {
    if (cursor === 0) {
      return;
    }

    setCursor(cursor - 1);
  }, [cursor]);

  const next = useCallback(() => {
    if (cursor === count - 1) {
      return;
    }

    setCursor(cursor + 1);
  }, [cursor, count]);

  return (
    <CarouselBlock>
      <CarouselWarpper cursor={cursor}>
        {children}
      </CarouselWarpper>
      {cursor > 0 && <LeftButton onClick={prev}/>}
      {cursor < count - 1 && <RightButton onClick={next}/>}
    </CarouselBlock>
  );
};

export default Carousel;
