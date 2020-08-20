import ReactGA from "react-ga";

export const initializeGoogleAnalytics = () => {
  ReactGA.initialize("UA-175432383-1");
};

export const googleAnalyticsPageView = (page: string) => {
  ReactGA.pageview(page);
};

export const googleAnalyticsEvent = (category: string, action: string) => {
  ReactGA.event({
    category,
    action,
  });
};

export const googleAnalyticsException = (description: string) => {
  ReactGA.exception({description});
};
