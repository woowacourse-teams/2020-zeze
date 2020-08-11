import React, { useRef, useEffect } from "react";
import ChartJS from "chart.js";

const COLORS = ["#55efc4", "#81ecec", "#74b9ff", "#a29bfe", "#ffeaa7", "#fab1a0"];

enum Type {
    BAR = "bar",
    HBAR = "horizontalBar",
    LINE = "line",
    RADAR = "radar",
    PIE = "pie",
    DOUGHNUT = "doughnut",
    POLAR_AREA = "polarArea",
    BUBBLE = "bubble",
    SCATTER = "scatter"
}

const parse = (text: string) => {
    const csvReader = createSimpleCsvReader(text);
    const head = readSimpleCsvHead(csvReader);
    const body = readSimpleCsvBody(csvReader);
    let json;

    try {
        const jsonString = Array.from(csvReader)
            .map(line => line.join("")
                .replace(/(.+):/, '"$1":'))
            .join(",");

        json = JSON.parse(`{${jsonString}}`);
    } catch {
        json = {};
    }
    return [head, body, json];
};

function* createSimpleCsvReader(text: string) {
    const lines = text.split("\n");

    for (const line of lines) {
        yield line.split(",")
            .map(value => value.trim());
    }
}

const readSimpleCsvHead = (csvReader: Generator<string[]>) => {
    let csv;

    while ((csv = csvReader.next()) && !csv.done) {
        if (csv.value) {
            return csv.value;
        }
    }
};

const readSimpleCsvBody = (csvReader: Generator<string[]>) => {
    let csv;
    const body = [];

    while ((csv = csvReader.next()) && !csv.done) {
        if (!csv.value.join("")) {
            return body;
        }
        body.push(csv.value);
    }
    return body;
};

const convertLabels = (head: string[], body: string[][], type: string) => {
    switch (type) {
        case Type.BAR:
        case Type.HBAR:
        case Type.LINE:
        case Type.RADAR:
            return body.map((row: string[]) => row[0]);
    }
    return head;
};

const convertDataSets = (head: string[], body: string[][], type: string) => {
    switch (type) {
        case Type.BAR:
        case Type.HBAR:
        case Type.LINE:
        case Type.RADAR:
            return head.map((title, i) => ({
                label: title,
                data: body.map(row => parseFloat(row[i + 1])),
                borderColor: COLORS[i % COLORS.length],
                backgroundColor: `${COLORS[i % COLORS.length]}88`,
                borderWidth: 1
            }));
        case Type.PIE:
        case Type.DOUGHNUT:
        case Type.POLAR_AREA:
            const colors = [...Array(head.length)].map((_, i) => COLORS[i % COLORS.length]);

            return body.map(row => ({
                data: row.map(column => parseFloat(column)),
                backgroundColor: colors,
            }));
        case Type.BUBBLE:
        case Type.SCATTER:
            const data = [head, ...body].reduce((previous: any, column: string[]) => ({
                ...previous,
                [column?.[0]]: [
                    ...previous?.[column?.[0]] ?? [], {
                        x: parseFloat(column?.[1]),
                        y: parseFloat(column?.[2]),
                        r: parseFloat(column?.[3]),
                    },
                ],
            }), {});

            return Object.keys(data).map((key, i) => ({
                label: key,
                data: data[key],
                borderColor: COLORS[i % COLORS.length],
                backgroundColor: COLORS[i % COLORS.length],
            }));
    }
};

interface IProps {
    code: string,
}

const Chart: React.FC<IProps> = ({ code = "" }) => {
    const canvas = useRef<HTMLCanvasElement>(null);

    useEffect(() => {
        const context = canvas.current!.getContext("2d")!;
        const [head, body, json] = parse(code);
        const type = json?.type ?? Type.BAR;

        try {
            const chart = new ChartJS(context, {
                type,
                data: {
                    labels: convertLabels(head, body, type),
                    datasets: convertDataSets(head, body, type),
                },
            });

            return () => {
                chart.destroy();
            };
        } catch (e) {
            console.warn(e.message);
        }
    }, [code]);

    return <canvas ref={canvas} />;
};

export default Chart;
