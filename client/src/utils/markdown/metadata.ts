export interface MetaProps {
    title: string;
    subtitle?: string;
    author?: string;
    createdAt?: string;
}

const METADATA_REGEX = /---\n(.+?)\n---/s;
const NEW_LINE_SEPARATOR = "\n";
const KEY_VALUE_SEPARATOR = ":";

const DEFAULT_PROPS: MetaProps = {
  title: "무제",
};

const parse = (value?: string) => {
  const matches = value?.match(METADATA_REGEX);
  const metadata = matches?.[1].split(NEW_LINE_SEPARATOR)
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

    firstPage = `# ${title}
## ${subtitle}
### ${author}
### ${createdAt}
---
`;
  }

  return {
    metadata,
    content: value?.replace(matches?.[0] ?? "", firstPage),
  };
};

export default parse;
