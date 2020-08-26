import React, {useCallback} from "react";
import {Link} from "react-router-dom";
import moment from 'moment';
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

    > header > img {
      opacity: 1;
    }
  }
  
  > header {
    display: flex;
    justify-content: flex-end;
    align-items: center;
    border-radius: 5px 5px 0 0;
    height: 28px;
    background-color: #747d8c;

    > img {
      width: 14px;
      height: 14px;
      border-radius: 3px;
      padding: 4px;
      background-color: rgb(0, 0, 0, 0.05);
      margin: 3px;
      opacity: 0;
      transition: opacity 0.3s;
      
      &:hover {
        background-color: rgb(0, 0, 0, 0.2);
      }
    }
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
    
    > div.presented_at {  
      font-size: 0.8rem;
    }
    
    > div.created_at {
      font-size: 0.815rem;
      color: gray;
    }
  }
`;


interface IProps {
  id: number,
  title?: string,
  subtitle?: string,
  author?: string,
  createdAt: string,
  presentedAt?: string,
  onClone?: (id: number) => void,
  onDelete?: (id: number) => void,
}

const titleLimitLength = 12;
const subTitleLimitLength = 20;
const authorNameLimitLength = 15;
const presentedAtLimitLength = 15;

const createStringSummary = (originData: string | undefined, limitLength: number, defaultData: string) => {
  if (!originData) {
    return defaultData
  }
  if (originData.length > titleLimitLength) {
    return `${originData.substring(0, titleLimitLength)}...`
  }
  return originData
}

const Card: React.FC<IProps> = ({id, title, subtitle, author, createdAt, presentedAt, onClone, onDelete}) => {
  const parsedTime = moment(createdAt).format('YYYY-MM-DD HH:mm:ss');

  const handleClickClone = useCallback((e: React.MouseEvent) => {
    e.preventDefault();
    onClone?.(id);
  }, [onClone, id]);

  const handleClickDelete = useCallback((e: React.MouseEvent) => {
    e.preventDefault();
    onDelete?.(id);
  }, [onDelete, id]);

  return (
    <Link to={`/editor/${id}`}>
      <CardBlock>
        <header>
          {onClone && <img onClick={handleClickClone} title="clone" src="/assets/icons/save.svg" alt="clone"/>}
          {onDelete && <img onClick={handleClickDelete} title="delete" src="/assets/icons/clear.svg" alt="delete"/>}
        </header>
        <main>
          <div className="title">{createStringSummary(title, titleLimitLength, "Title")}</div>
          <div className="subtitle">{createStringSummary(subtitle, subTitleLimitLength, "Subtitle")}</div>
          <div className="author">{createStringSummary(author, authorNameLimitLength, "Author")}</div>
          <div className="presented_at">{createStringSummary(presentedAt, presentedAtLimitLength, "Presentation Date")}</div>
          <div className="created_at">{parsedTime}</div>
        </main>
      </CardBlock>
    </Link>
  );
};

export default Card;
