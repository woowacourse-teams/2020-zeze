import React, {useCallback, useEffect, useState} from 'react';
import {AxiosResponse} from "axios";
import styled from "@emotion/styled";

import Pagination from "./Pagination";
import Cards from "./Cards";
import {PageProps, MetaDataResponses, MetaDataResponse, SlideResponse} from "../../api/slide";
import {googleAnalyticsPageView} from "../../utils/googleAnalytics";
import ConfirmModal from './ConfirmModal';
import ToastFactory from '../../domains/ToastFactory';
import {ToastType, AccessLevel} from '../../domains/constants';

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
  accessLevel?: AccessLevel
}

const SlidesLayout: React.FC<IProps> = ({getAllSlides, cloneSlide, deleteSlide, slidesCnt, title, accessLevel = AccessLevel.PUBLIC}) => {
  const [slides, setSlides] = useState<Array<MetaDataResponse>>([]);
  const [page, setPage] = useState<number>(0);
  const [totalPage, setTotalPage] = useState<number>(1);
  const [selectedId, setSelectedId] = useState<number>(0);
  const toastFactory = ToastFactory();

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
  }, []);

  const confirmDelete = useCallback((id: number) => {
    setSelectedId(id);
  }, []);

  const onDeleteSlide = useCallback(() => {
    const selectedSlide = slides.find(slide => slide.id === selectedId);
    if (!selectedSlide) {
      return;
    }
    deleteSlide?.(selectedId).then(() => {
      setSlides(slides.filter(slide => slide !== selectedSlide));
      setSelectedId(0);
      toastFactory.createToast(`delete ${selectedSlide.title}!`, ToastType.SUCCESS);
    });
  }, [deleteSlide, slides, selectedId, toastFactory]);

  const onCloneSlide = useCallback((id: number) => {
    const slide = slides.find(slide => slide.id === id);
    if (!slide) {
      return;
    }
    cloneSlide?.(id).then(({headers: {location}}) => {
      const slideId = parseInt(location.substring(location.lastIndexOf("/") + 1));
       accessLevel === AccessLevel.PRIVATE && setSlides([
        {
          ...slide,
          id: slideId,
          title: `${slide.title} (clone)`,
          createdAt: new Date().toString(),
        },
        ...slides,
      ]);
      toastFactory.createToast(`clone ${slide.title}!`, ToastType.SUCCESS);
    });
  }, [cloneSlide, slides, toastFactory]);

  useEffect(() => {
    googleAnalyticsPageView("Archive");
  }, []);

  return (
    <SlidesBlock>
      <Cards onClone={cloneSlide && onCloneSlide} onDelete={deleteSlide && confirmDelete} title={title} slides={slides}/>
      <Pagination page={page}
                  totalPage={totalPage}
                  onClickPage={onClickPage}
                  onClickMove={onClickMove}/>
      <ConfirmModal visibility={selectedId !== 0} onBackdropClick={() => setSelectedId(0)} onSubmit={onDeleteSlide} onCancel={() => setSelectedId(0)}>
        Are you sure to delete?
      </ConfirmModal>
    </SlidesBlock>
  );
};

export default SlidesLayout;
