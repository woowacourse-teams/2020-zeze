import axios, {AxiosResponse} from "axios";

interface Props {
  id: number | undefined,
  data?: {
    title: string
    content: string
    contentType: string
    createdAt?: string
    updatedAt?: string
  }
}

const slideInstance = axios.create({
  baseURL: "localhost:8080/api/slides",
  headers: {authorization: localStorage.getItem("token")},
});

export const slide = {
  get({ id }: Props): Promise<AxiosResponse<Props>> {
    return slideInstance.get(`${id}`);
  },
  getAll(): Promise<AxiosResponse<Array<Props>>> {
    return slideInstance.get("/");
  },
  create({ data }: Props): Promise<AxiosResponse<Props>> {
    return slideInstance.post("/", data);
  },
  update({ id, data }: Props): Promise<AxiosResponse<Props>> {
    return slideInstance.put(`${id}`, data);
  },
  delete({ id }: Props): Promise<AxiosResponse<Props>> {
    return slideInstance.delete(`${id}`);
  },
};

const slidesInstance = axios.create({
  baseURL: "localhost:8080/api/search",
});

export const slides = {
  getAll(): Promise<AxiosResponse<Array<Props>>> {
    return slidesInstance.get("/");
  },
};

