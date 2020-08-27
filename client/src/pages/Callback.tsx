/* eslint-disable react-hooks/exhaustive-deps */
import React, {useEffect} from "react";
import {useLocation, useHistory} from "react-router-dom";
import axios from "axios";
import GlobalLayout from "../components/common/GlobalLayout";
import Spinner from "../components/common/Spinner";
import {googleAnalyticsException, googleAnalyticsPageView} from "../utils/googleAnalytics";
import ToastFactory from "../domains/ToastFactory";
import {ToastType} from "../domains/constants";

interface props {
  location: string,
  history: string[]
}

const Callback: React.FC<props> = () => {
  const location = useLocation();
  const history = useHistory();
  const githubBaseUrl = `/api/signin/github`;
  const toastFactory = ToastFactory();

  useEffect(() => {
    googleAnalyticsPageView("Github Login Callback");
  }, []);

  useEffect(() => {
    const getToken = async () => {
      const code: string | null = new URLSearchParams(location.search.toString()).get("code");
      const accessToken: string = (await axios.post(githubBaseUrl, {
        provider: "GITHUB",
        code: code || "",
      })).data.accessToken;

      localStorage.setItem("accessToken", `bearer ${accessToken}`);
    };

    getToken()
      .then(() => history.replace("/me"))
      .catch(() => {
        googleAnalyticsException("로그인 실패");
        toastFactory.createToast("Fail to login", ToastType.ERROR);
        history.replace("/");
      });
  }, [location, history, githubBaseUrl]);

  return (
    <GlobalLayout>
      <Spinner/>
    </GlobalLayout>
  );
};

export default Callback;
