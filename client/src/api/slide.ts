import axios, {AxiosResponse} from "axios";
import {AccessLevel} from "../domains/constants";

export interface SlideRequest {
  id?: number
  data?: {
    title: string
    subtitle: string
    author: string
    presentedAt: string
    content: string
    accessLevel: AccessLevel
    createdAt?: string
    updatedAt?: string
  }
}

export interface SlideResponse {
  content: string
  accessLevel: AccessLevel
  updatedAt: string
}

export interface MetaDataResponse {
  id: number;
  title?: string;
  subtitle?: string;
  author?: string;
  presentedAt?: string;
  createdAt: string;
}

export interface MetaDataResponses {
  slides: Array<MetaDataResponse>
  totalPage: number
}

export interface PageProps {
  page: number
  size: number
}

const slideInstance = axios.create({
  baseURL: `/api/slides`,
});

const slideApi = {
  get(id: number): Promise<AxiosResponse<SlideResponse>> {
    return slideInstance.get(`${id}`, {
      headers: {
        authorization: localStorage.getItem("accessToken"),
      },
    });
  },
  getAll(page: PageProps): Promise<AxiosResponse<MetaDataResponses>> {
    return slideInstance.get("/", {
      headers: {
        authorization: localStorage.getItem("accessToken"),
      },
      params: page,
    });
  },
  getPublic(page: PageProps): Promise<AxiosResponse<MetaDataResponses>> {
    return slideInstance.get("/", {
      params: page,
    });
  },
  create({data}: SlideRequest): Promise<AxiosResponse<SlideResponse>> {
    return slideInstance.post("/", data, {
      headers: {
        authorization: localStorage.getItem("accessToken"),
      },
    });
  },
  update({id, data}: SlideRequest): Promise<AxiosResponse<SlideResponse>> {
    return slideInstance.patch(`${id}`, data, {
      headers: {
        authorization: localStorage.getItem("accessToken"),
      },
    });
  },
  delete(id: number): Promise<AxiosResponse<SlideResponse>> {
    return slideInstance.delete(`${id}`, {
      headers: {
        authorization: localStorage.getItem("accessToken"),
      },
    });
  },
};

export default slideApi;

