import React, {useEffect, useState} from "react";
import styled from "@emotion/styled";
import SidebarLayout from "../components/common/SidebarLayout";
import Cards from "../components/common/Cards";
import Pagination from "../components/common/Pagination";
import slideApi, {SlideResponse} from "../api/slide";
import {googleAnalyticsPageView} from "../utils/googleAnalytics";

const ArchiveBlock = styled.div`
display: flex;
flex-direction: column;
justify-content: space-between;
height: 100%;
`;

const Archive: React.FC = () => {
  const size = 9;
  const [slides, setSlides] = useState<Array<SlideResponse>>([]);
  const [page, setPage] = useState<number>(0);
  const [totalPage, setTotalPage] = useState<number>(1);

  useEffect(() => {
    slideApi.getAll({page, size})
      .then(res => {
        setSlides(res.data.slides);
        setTotalPage(res.data.totalPage);
      });
  }, [page]);

  const onClickPage = (e: React.MouseEvent<HTMLDivElement>) => {
    const page = parseInt(e.currentTarget.getAttribute("data-page")!);

    setPage(page);
    slideApi.getAll({page, size})
      .then(res => setSlides(res.data.slides));
  };

  const onClickPrevious = () => {
    setPage(page - 1);
    slideApi.getAll({page, size})
      .then(res => setSlides(res.data.slides));
  };

  const onClickNext = () => {
    setPage(page + 1);
    slideApi.getAll({page, size})
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
                    onClickPrevious={onClickPrevious}
                    onClickNext={onClickNext}/>
      </ArchiveBlock>
    </SidebarLayout>
  );
};

export default Archive;
