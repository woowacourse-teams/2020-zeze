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

export const slide = {
  get({id}: Props): Promise<AxiosResponse<Props>> {
    return axios(
      {
        method: "GET",
        url: `localhost:8080/api/slides?id=${id}`,
        headers: {authorization: localStorage.getItem("token")},
      },
    );
  },
  getAll(): Promise<AxiosResponse<Array<Props>>> {
    return axios({
      method: "GET",
      url: `localhost:8080/api/slides`,
      headers: {authorization: localStorage.getItem("token")},
    },
    );
  },
  create({data}: Props): Promise<AxiosResponse<Props>> {
    return axios(
      {
        method: "POST",
        url: "localhost:8080/api/slides",
        headers: {authorization: localStorage.getItem("token")},
        data,
      });
  },
  update({id, data}: Props): Promise<AxiosResponse<Props>> {
    return axios(
      {
        method: "PUT",
        url: `localhost:8080/api/slides?id=${id}`,
        headers: {authorization: localStorage.getItem("token")},
        data,
      });
  },
  delete({id}: Props): Promise<AxiosResponse<Props>> {
    return axios(
      {
        method: "DELETE",
        url: `localhost:8080/api/slides?id=${id}`,
        headers: {authorization: localStorage.getItem("token")},
      },
    );
  },
};

export const slides = {
  getAll(): Promise<AxiosResponse<Array<Props>>> {
    return axios({
      method: "GET",
      url: `localhost:8080/api/search`,
    },
    );
  },
};

