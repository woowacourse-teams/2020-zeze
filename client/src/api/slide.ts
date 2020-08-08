import axios, {AxiosResponse} from "axios";

export interface SlideRequest {
  id?: number,
  data?: {
    title: string
    content: string
    accessLevel: string
    createdAt?: string
    updatedAt?: string
  }
}

export interface SlideResponse {
  id: number,
  title: string
  content: string
  accessLevel: string
  createdAt: string
  updatedAt: string
}

interface SlideResponses {
  slides: Array<SlideResponse>
}

interface PageProps {
  id: number,
  size: number
}

const slideInstance = axios.create({
  baseURL: "/api/slides",
  headers: {authorization: localStorage.getItem("token")},
});

const slideApi = {
  get({id}: SlideRequest): Promise<AxiosResponse<SlideResponse>> {
    return slideInstance.get(`${id}`);
  },
  getAll(page: PageProps): Promise<AxiosResponse<SlideResponses>> {
    return slideInstance.get("/", {
      params: {
        ...page,
      },
    });
  },
  create({data}: SlideRequest): Promise<AxiosResponse<SlideResponse>> {
    return slideInstance.post("/", data);
  },
  update({id, data}: SlideRequest): Promise<AxiosResponse<SlideResponse>> {
    return slideInstance.patch(`${id}`, data);
  },
  delete(id: number): Promise<AxiosResponse<SlideResponse>> {
    return slideInstance.delete(`${id}`);
  },
};

export default slideApi;

