import React, {useEffect, useState} from 'react';
import Pagination from "./Pagination";
import Cards from "./Cards";
import slideApi, {SlideResponse} from "../../api/slide";
import {googleAnalyticsPageView} from "../../utils/googleAnalytics";
import styled from "@emotion/styled";

const SlidesBlock = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100%;
`;

interface IProps {
  slidesCnt: number
  title: string
}

const SlidesLayout: React.FC<IProps> = ({slidesCnt, title}) => {
  const [slides, setSlides] = useState<Array<SlideResponse>>([]);
  const [page, setPage] = useState<number>(0);
  const [totalPage, setTotalPage] = useState<number>(1);

  useEffect(() => {
    slideApi.getAll({page, size: slidesCnt})
      .then(res => {
        setSlides(res.data.slides);
        setTotalPage(res.data.totalPage);
      });
  }, [page, totalPage]);

  const onClickPage = (e: React.MouseEvent<HTMLDivElement>) => {
    const page = parseInt(e.currentTarget.getAttribute("data-page")!);
    setPage(page);
    slideApi.getAll({page, size: slidesCnt})
      .then(res => setSlides(res.data.slides));
  };

  const onClickMove = (pageNum: number) => {
    setPage(pageNum);
    slideApi.getAll({page, size: slidesCnt})
      .then(res => setSlides(res.data.slides));
  };

  useEffect(() => {
    googleAnalyticsPageView("Archive");
  }, []);

  return (
    <SlidesBlock>
      <Cards title={title} slides={slides} author={"zeze"}/>
      <Pagination page={page}
                  totalPage={totalPage}
                  onClickPage={onClickPage}
                  onClickMove={onClickMove}/>
    </SlidesBlock>
  );
}

export default SlidesLayout;
