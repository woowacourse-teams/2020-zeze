import axios, {AxiosResponse} from "axios";

export interface SlideRequest {
  id?: number
  data?: {
    title: string
    content: string
    accessLevel: string
    createdAt?: string
    updatedAt?: string
  }
}

export interface SlideResponse {
  id: number
  title: string
  content: string
  accessLevel: string
  createdAt: string
  updatedAt: string
}

export interface SlideResponses {
  slides: Array<SlideResponse>
  totalPage: number
}

interface PageProps {
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
  getAll(page: PageProps): Promise<AxiosResponse<SlideResponses>> {
    return slideInstance.get("/", {
      headers: {
        authorization: localStorage.getItem("accessToken"),
      },
      params: {
        ...page,
      },
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

