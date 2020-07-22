interface Props {
  src?: string;
  width: string;
  height: string;
};

interface Youtube {
  id: string;
  width: string;
  height: string;
};

const YOUTUBE_ID_REGEX = /^(?:(?:https?)?(?::\/\/)?(?:www\.)?(?:youtu\.be\/|youtube.com\/watch\?v=|youtube.com\/embed\/)?)?(\S+)$/;
const NEW_LINE_SEPARATOR = "\n";
const KEY_VALUE_SEPARATOR = "=";

const DEFAULT_PROPS: Props = {
  width: "510",
  height: "315",
};

const template = ({ id, width, height }: Youtube): string =>
  `<iframe width="${width}" height="${height}" src="https://www.youtube.com/embed/${id}" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>`;

export default (code: string): string => {
  const { src, width, height } = code
    .split(NEW_LINE_SEPARATOR)
    .map((line) => {
      const [key, ...value] = line.split(KEY_VALUE_SEPARATOR);
      return [key, value.join(KEY_VALUE_SEPARATOR)];
    })
    .reduce(
      (previous, [key, value]) => ({
        ...previous,
        [key]: value,
      }),
      DEFAULT_PROPS
    );

  const id = src?.match(YOUTUBE_ID_REGEX)?.[1];

  return id ? template({ id, width, height }) : "";
};
