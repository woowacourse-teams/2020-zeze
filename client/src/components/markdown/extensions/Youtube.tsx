import React from "react";

const YOUTUBE_ID_REGEX = /^(?:(?:https?)?(?::\/\/)?(?:www\.)?(?:youtu\.be\/|youtube.com\/watch\?v=|youtube.com\/embed\/)?)?(\S+)$/;

interface IProps {
  code: string,
}

const Youtube: React.FC<IProps> = ({ code = "" }) => {
  const id = code.match(YOUTUBE_ID_REGEX)?.[1];

  return id ?
    <iframe
      className="youtube"
      title={id}
      width="100%"
      height="315"
      src={`https://www.youtube.com/embed/${id}`} frameBorder="0"
      allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"
      allowFullScreen /> : null;
};

export default Youtube;
