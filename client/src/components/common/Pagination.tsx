import React from 'react'
import styled from "@emotion/styled";

const PAGES = styled.div`
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
  const pageCnt = 5;
  const pageGroup = Math.floor(page / pageCnt) + 1;
  let last = pageGroup * pageCnt;
  const first = last - pageCnt;
  if (last > totalPage) {
    last = totalPage;
  }
  const next = last + 1;
  const prev = first - 1;

  const showPrevBtn = () => {
    if (first > pageCnt) {
      return <div onClick={() => onClickPrev(prev)}>&laquo;</div>;
    }
  }

  const showNextBtn = () => {
    if (next > pageCnt && next < totalPage) {
      return <div onClick={() => onClickNext(next)}>&raquo;</div>
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
    <PAGES>
      {showPrevBtn()}
      {showNumbers()}
      {showNextBtn()}
    </PAGES>
  );
}

export default Pagination;
