import moment from "moment";

export interface MetaProps {
  title?: string;
  subtitle?: string;
  author?: string;
  presentedAt?: string;
}

export interface ParsedData {
  metadata?: MetaProps,
  content: string
}

const METADATA_REGEX = /---\n(.+?)\n---/s;
const NEW_LINE_SEPARATOR = "\n";
const KEY_VALUE_SEPARATOR = ":";

const DEFAULT_PROPS: MetaProps = {
  title: "Untitled",
};

export const createTemplate = ({title, subtitle, author}: MetaProps) =>
  `---
title: ${title ?? "Untitled"}
subtitle: ${subtitle ?? "Untitled"}
author: ${author ?? "Anonymous"}
presentedAt: ${moment().format('YYYY-MM-DD')}
---
`;

export const parse = (text: string): ParsedData => {
  const matches = text.match(METADATA_REGEX);
  const metadata: MetaProps | undefined = matches?.[1].split(NEW_LINE_SEPARATOR)
    .map(line => {
      const [key, ...value] = line.split(KEY_VALUE_SEPARATOR);

      return [key, value.join(KEY_VALUE_SEPARATOR).trim()];
    })
    .reduce((previous, [key, value]) => ({
      ...previous,
      [key]: value,
    }), DEFAULT_PROPS);

  let firstPage = "";

  if (metadata) {
    const {author, title, subtitle, presentedAt} = metadata;

    firstPage = firstPage
      .concat(`<div class="first-page">`)
      .concat(title ? `<h1 class="title">${title}</h1>` : "")
      .concat(subtitle ? `<h2 class="subtitle">${subtitle}</h2>` : "")
      .concat(author ? `<h3 class="author">${author}</h3>` : "")
      .concat(presentedAt ? `<h4 class=presented-at">${presentedAt}</h4>` : "")
      .concat("</div>")
      .concat("\n\n---\n\n");
  }

  return {
    metadata,
    content: text.replace(matches?.[0] ?? "", firstPage),
  };
};
