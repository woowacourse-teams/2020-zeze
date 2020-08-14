export interface MetaProps {
  title: string;
  subtitle?: string;
  author?: string;
  createdAt?: string;
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

const parse = (text: string): ParsedData => {
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
    const {author, title, subtitle, createdAt} = metadata;

    firstPage = firstPage
      .concat(`<div class="first-page">`)
      .concat(title ? `<h1 class="title">${title}</h1>` : "")
      .concat(subtitle ? `<h2 class="subtitle">${subtitle}</h2>` : "")
      .concat(author ? `<h3 class="author">${author}</h3>` : "")
      .concat(createdAt ? `<h4 class="created-at">${createdAt}</h4>` : "")
      .concat("</div>")
      .concat("\n\n---\n\n");
  }

  return {
    metadata,
    content: text.replace(matches?.[0] ?? "", firstPage),
  };
};

export default parse;
