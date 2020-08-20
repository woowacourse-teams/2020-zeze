import React, {useEffect, useState} from 'react'
import styled from "@emotion/styled";
import {PAGE_CNT} from "../../domains/constants";

const Pages = styled.div`
  width: 100%;
  height: 30px;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 30px 0;
  
  div {
    display: flex;
    justify-content: center;
    align-items: center;  
    width: 30px;
    height: 100%;
    margin: 0 5px;
    color: white;
  }
  
  div.active {
    background-color: #F0FFFF;
    color: black;
    border-radius: 50%;
  }
  
  div:hover:not(.active) {
    background-color: #ddd;
    border-radius: 50%;
    cursor: pointer;
  }
`

interface IProps {
  page: number
  totalPage: number
  onClickPage: (e: React.MouseEvent<HTMLDivElement>) => void
  onClickPrev: (prev: number) => void
  onClickNext: (next: number) => void
}

const Pagination: React.FC<IProps> = ({page, totalPage, onClickPage, onClickPrev, onClickNext}) => {
  const [first, setFirst] = useState<number>(0);
  const [last, setLast] = useState<number>(0);

  useEffect(() => {
    const pageGroup = Math.floor(page / PAGE_CNT) + 1;
    let last = pageGroup * PAGE_CNT;
    const first = last - PAGE_CNT;
    if (last > totalPage) {
      last = totalPage;
    }
    setFirst(first);
    setLast(last);
  }, [page])

  const showPrevBtn = () => {
    if (first > PAGE_CNT) {
      return <div onClick={() => onClickPrev(first - 1)}>&laquo;</div>;
    }
  }

  const showNextBtn = () => {
    if (last + 1 > PAGE_CNT && last + 1 < totalPage) {
      return <div onClick={() => onClickNext(last + 1)}>&raquo;</div>
    }
  }

  const showNumbers = () => {
    const elements: JSX.Element[] = [];
    for (let i = first; i < last; i++) {
      if (i === page) {
        elements.push(<div data-page={i} onClick={onClickPage} className="active">{i + 1}</div>);
      } else {
        elements.push(<div data-page={i} onClick={onClickPage}>{i + 1}</div>);
      }
    }
    return elements;
  }

  return (
    <Pages>
      {showPrevBtn()}
      {showNumbers()}
      {showNextBtn()}
    </Pages>
  );
}

export default Pagination;