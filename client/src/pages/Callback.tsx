import React, {useEffect} from "react";
import axios from "axios";
import GlobalLayout from "../components/common/GlobalLayout";

interface props {
  location: string,
  history: string[]
}

const Callback: React.FC<props> = ( {location, history} :props ) => {
  const githubBaseUrl: string = "/api/signin/github";

  useEffect(() => {
    getToken()
      .then(() => history.push("/me"))
      .catch(() => history.push("/error"));
  }, [location, history]);

  const getToken = async () => {
    const code: string = location.search.toString().split("=")[1];
    const accessToken: string = (await axios.post(githubBaseUrl, {
      provider: "GITHUB",
      code,
      })).data.accessToken;
    localStorage.setItem("accessToken", `bearer ${accessToken}`);
  };

  return (
    <GlobalLayout>
    <div>잠시만 기다려주세요.</div>
    </GlobalLayout>
  );
};
export default Callback;
