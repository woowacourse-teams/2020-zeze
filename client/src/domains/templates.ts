import heading from "../assets/icons/editor/heading.svg";
import image from "../assets/icons/editor/image.svg";
import link from "../assets/icons/editor/link.svg";
import bullets from "../assets/icons/editor/bullets.svg";
import list from "../assets/icons/editor/list.svg";
import table from "../assets/icons/editor/table.svg";
import quote from "../assets/icons/editor/quote.svg";
import youtube from "../assets/icons/editor/youtube.svg";
import chart from "../assets/icons/editor/chart.svg";
import code from "../assets/icons/editor/code.svg";
import page from "../assets/icons/editor/page.svg";
import vim from "../assets/icons/editor/vim.svg";

export default [
  {
    title: "Page",
    src: page,
    template: "\n---\n\nNew page!\n",
  }, {
    title: "Heading",
    src: heading,
    template: "\n# title\n",
  }, {
    title: "Image",
    src: image,
    template: "\n![alt text](https://picsum.photos/200)\n",
  }, {
    title: "Link",
    src: link,
    template: "\n[link](http://example.com/)\n",
  }, {
    title: "Bullets",
    src: bullets,
    template: `
- this
  - example
- acts like
- a unordered list 
`,
  }, {
    title: "List",
    src: list,
    template: `
1. this
   1. example
1. acts like
1. a ordered list 
`,
  }, {
    title: "Table",
    src: table,
    template: `
| Tables   |      Are      |  Cool |
|----------|:-------------:|------:|
| col 1 is |  left-aligned | $1600 |
| col 2 is |    centered   |   $12 |
| col 3 is | right-aligned |    $1 |
`,
  }, {
    title: "Quote",
    src: quote,
    template: `
> quotes!
>
> type something cool 
`,
  }, {
    title: "Youtube",
    src: youtube,
    template: `
\`\`\`youtube
ocwnns57cYQ
\`\`\`
`,
  }, {
    title: "Chart",
    src: chart,
    template: `
\`\`\`chart
Jay,Lowoon,Hodol,Orange,Finn
MON,1,2,3,4,5
TUE,2,3,4,5,6
WED,3,4,5,6,7
THU,4,5,6,7,8
FRI,5,6,7,8,9
SAT,6,7,8,9,10
SUN,7,8,9,10,11

<!-- available types: horizontalBar, line, radar, pie, doughnut, polarArea, bubble, scatter -->

type:"horizontalBar"
\`\`\`
`,
  }, {
    title: "Code",
    src: code,
    template: `
\`\`\`ts
function greetings(name: string) {
  return \`hello, \${name}!\`
}
\`\`\`
`,
  }, {
    title: "Vim",
    src: vim,
    template: `vim`,
  },
];
