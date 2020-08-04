import React from "react";
import {Link} from "react-router-dom";
import styled from "@emotion/styled";
import {ZEZE_GRAY} from "../../domains/constants";

const CardBlock = styled.div`
  background: #fff;
  border-radius: 5px;
  height: 200px;
  position: relative;
  box-shadow: 0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24);
  transition: all 0.3s cubic-bezier(.25,.8,.25,1);
  display: flex;
  justify-content: space-between;
  flex-direction: column;
  
  &:hover {
    box-shadow: 0 14px 28px rgba(0,0,0,0.25), 0 10px 10px rgba(0,0,0,0.22);
    cursor: pointer;
  }
  
  > header {
    border-radius: 5px 5px 0 0;
    height: 12px;
    background-color: springgreen;
  }
  
  > main {
    padding: 15px;
    
    > div.title {
      font-size: 1.75rem;
      margin-bottom: 0.25rem;
    }
    
    > div.subtitle {
      font-size: 0.815rem;
      margin-bottom: 1.5rem;
      color: ${ZEZE_GRAY};
    }
    
    > div.author {
      font-size: 0.815rem;
    }
    
    > div.created_at {
      font-size: 0.815rem;
      color: gray;
    }
  }
`;


interface IProps {
  id: number,
  title: string,
  subtitle: string,
  author: string,
  createdAt: string
}

const Card: React.FC<IProps> = ({id, title, subtitle, author, createdAt}) => (
  <Link to={`/editor/${id}`}>
    <CardBlock>
      <header/>
      <main>
        <div className="title">{title}</div>
        <div className="subtitle">{subtitle}</div>
        <div className="author">{author}</div>
        <div className="created_at">{createdAt}</div>
      </main>
    </CardBlock>
  </Link>
);

export default Card;
