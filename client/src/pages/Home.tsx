import React, {useEffect} from "react";
import styled from "@emotion/styled";
import GlobalLayout from "../components/common/GlobalLayout";
import {GITHUB_AUTH_URL, MAX_WIDTH, MOBILE_MAX_WIDTH, ZEZE_GRAY} from "../domains/constants";
import {googleAnalyticsEvent, googleAnalyticsPageView} from "../utils/googleAnalytics";

export const HomeBlock = styled.div`
  background-color: ${ZEZE_GRAY};
`;

export const Layout = styled.main`
  padding: 15px;
  max-width: ${MAX_WIDTH}px;
  margin: 0 auto;
`;

export const Slogan = styled.h2`
  margin: 0;
  color: #fff;
  font-size: 6rem;
  
  @media (max-width: ${MOBILE_MAX_WIDTH}px) {
    font-size: 2.5rem;
  }
`;

interface SectionProps {
  background: string;
}

export const Section = styled("div")<SectionProps>`
  background-color: ${({background}: SectionProps) => background};
  padding: 60px 15px;
  margin: 0 auto;
  
  @media (max-width: ${MOBILE_MAX_WIDTH}px) {
    padding: 30px 15px;
  }
`;

export const Button = styled.a`
  text-decoration: none;
  display: inline-block;
  border: 2px solid white;
  border-radius: 50px;
  padding: 1rem 1.5rem;
  margin: 2rem 0 1.5rem;
  color: #fff;
  font-weight: 600;
  font-size: 1.4rem;
  
  &:hover {
    cursor: pointer;
  }
  
  @media (max-width: ${MOBILE_MAX_WIDTH}px) {
    margin: 1rem 0 0.75rem;
    font-size: 1.1rem;
  }
`;

const Home: React.FC = () => {
  useEffect(() => {
    googleAnalyticsPageView("Landing");
  }, []);

  return (
    <GlobalLayout>
      <HomeBlock>
        <Section background={ZEZE_GRAY}>
          <Layout>
            <Slogan>Beautiful,</Slogan>
            <Slogan>Minimal <span style={{color: "#00FF7F"}}>Slides</span></Slogan>
            <Slogan>With Markdown</Slogan>
            <Button href={GITHUB_AUTH_URL} onClick={() => googleAnalyticsEvent("User", "Get Started")}>Get Started for Free →</Button>
          </Layout>
        </Section>
        <Section background="#333">
          <Layout>
            <code style={{color: "#fff"}}>
              --- <br/>
              title: Hello Limetree! <br/>
              author: Hodol <br/>
              created_at: 2020-07-12 <br/>
              --- <br/> <br/>
              ## Works like charm <br/>
              ### with minimal effort <br/> <br/>
              - Only need to type <br/>
              - Supports GFM Markdown <br/>
              - Youtube, Charts, and more! <br/> <br/>
              --- <br/> <br/>
              ## Focus on your idea <br/> <br/>
              > No more decorating stuff <br/>
              > Pixel perfect beautiful slides
            </code>
          </Layout>
        </Section>
        <Section background={ZEZE_GRAY}>
          <div style={{
            width: "100%",
            maxWidth: MAX_WIDTH,
            margin: "0 auto",
            minHeight: "30rem",
            backgroundColor: "#fff",
            fontWeight: "bold",
            fontSize: 20,
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            boxShadow: "1px 1px 1px #aaa",
          }}>
            Slide example goes here
          </div>
        </Section>
      </HomeBlock>
    </GlobalLayout>
  );
};

export default Home;
