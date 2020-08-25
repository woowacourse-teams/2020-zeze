import React, {useCallback, useEffect, useState} from 'react';
import {AxiosResponse} from "axios";
import styled from "@emotion/styled";

import Pagination from "./Pagination";
import Cards from "./Cards";
import {PageProps, MetaDataResponses, MetaDataResponse, SlideResponse} from "../../api/slide";
import {googleAnalyticsPageView} from "../../utils/googleAnalytics";

const SlidesBlock = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100%;
`;

interface IProps {
  getAllSlides: (page: PageProps) => Promise<AxiosResponse<MetaDataResponses>>
  cloneSlide?: (id: number) => Promise<AxiosResponse<SlideResponse>>
  deleteSlide?: (id: number) => Promise<AxiosResponse<SlideResponse>>
  slidesCnt: number
  title: string
}

const SlidesLayout: React.FC<IProps> = ({getAllSlides, cloneSlide, deleteSlide, slidesCnt, title}) => {
  const [slides, setSlides] = useState<Array<MetaDataResponse>>([]);
  const [page, setPage] = useState<number>(0);
  const [totalPage, setTotalPage] = useState<number>(1);

  useEffect(() => {
    getAllSlides({page, size: slidesCnt})
      .then(res => {
        setSlides(res.data.slides);
        setTotalPage(res.data.totalPage);
      });
  }, [page, slidesCnt, getAllSlides]);

  const onClickPage = useCallback((e: React.MouseEvent<HTMLDivElement>) => {
    const page = parseInt(e.currentTarget.getAttribute("data-page")!);
    setPage(page);
  }, []);

  const onClickMove = useCallback((pageNum: number) => {
    setPage(pageNum);
  },[]);

  const onDeleteSlide = useCallback((id: number) => {
    deleteSlide?.(id).then(() => {
      setSlides(slides.filter(slide => slide.id !== id));
    });
  }, [deleteSlide, slides]);

  const onCloneSlide = useCallback((id: number) => {
    cloneSlide?.(id).then(res => {
      setSlides([
        // ...slides,
        // res.data
      ]);
    });
  }, [cloneSlide, slides]);

  useEffect(() => {
    googleAnalyticsPageView("Archive");
  }, []);

  return (
    <SlidesBlock>
      <Cards onClone={onCloneSlide} onDelete={onDeleteSlide} title={title} slides={slides}/>
      <Pagination page={page}
                  totalPage={totalPage}
                  onClickPage={onClickPage}
                  onClickMove={onClickMove}/>
    </SlidesBlock>
  );
};

export default SlidesLayout;
