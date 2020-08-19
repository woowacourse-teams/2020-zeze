import React from 'react'
import styled from "@emotion/styled";

const PAGES = styled.div`
  width: 100%;
  height: 30px;
  display: flex;
  justify-content: center;
  align-items: center;
  
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
  onClickPrevious: () => void
  onClickNext: () => void
}

const Pagination: React.FC<IProps> = ({page, totalPage, onClickPage, onClickPrevious, onClickNext}) => {
  const pageCnt = 5;
  const pageGroup = Math.floor(page / pageCnt) + 1;
  let last = pageGroup * pageCnt;
  if (last > totalPage)
    last = totalPage;
  let first = last - (pageCnt - 1);
  if (totalPage < 1)
    first = last;
  const next = last + 1;

  const showPrevBtn = () => {
    if (first > 5) {
      return <div onClick={onClickPrevious}>&laquo;</div>;
    }
  }

  const showNextBtn = () => {
    if (next > 5 && next < totalPage) {
      return <div onClick={onClickNext}>&raquo;</div>
    }
  }

  const showNumbers = () => {
    const elements: JSX.Element[] = [];
    for (let i = first; i < last; i++) {
      if (i === page) {
        elements.push(<div data-page={i} onClick={onClickPage} className="active">{i + 1}</div>);
      } else if (i > 0) {
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
