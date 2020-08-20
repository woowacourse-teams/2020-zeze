import React, {useEffect, useState} from "react"
import styled from "@emotion/styled";
import SidebarLayout from "../components/common/SidebarLayout";
import Cards from "../components/common/Cards";
import Pagination from "../components/common/Pagination";
import slideApi, {SlideResponse} from "../api/slide";
import {googleAnalyticsPageView} from "../utils/googleAnalytics";
import {SLIDES_CNT} from "../domains/constants";

const ArchiveBlock = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100%;
`;

const Archive: React.FC = () => {
  const [slides, setSlides] = useState<Array<SlideResponse>>([]);
  const [page, setPage] = useState<number>(0);
  const [totalPage, setTotalPage] = useState<number>(1);

  useEffect(() => {
    slideApi.getAll({page, size: SLIDES_CNT})
      .then(res => {
        setSlides(res.data.slides);
        setTotalPage(res.data.totalPage);
      });
  }, [page]);

  const onClickPage = (e: React.MouseEvent<HTMLDivElement>) => {
    const page = parseInt(e.currentTarget.getAttribute("data-page")!);
    setPage(page);
    slideApi.getAll({page, size: SLIDES_CNT})
      .then(res => setSlides(res.data.slides));
  };

  const onClickPrev = (prev: number) => {
    setPage(prev);
    slideApi.getAll({page, size: SLIDES_CNT})
      .then(res => setSlides(res.data.slides));
  };

  const onClickNext = (next: number) => {
    setPage(next);
    slideApi.getAll({page, size: SLIDES_CNT})
      .then(res => setSlides(res.data.slides));
  };

  useEffect(() => {
    googleAnalyticsPageView("Archive");
  }, []);

  return (
    <SidebarLayout>
      <ArchiveBlock>
        <Cards title="Archive" slides={slides} author={"zeze"}/>
        <Pagination page={page}
                    totalPage={totalPage}
                    onClickPage={onClickPage}
                    onClickPrev={onClickPrev}
                    onClickNext={onClickNext}/>
      </ArchiveBlock>
    </SidebarLayout>
  );
};

export default Archive;
