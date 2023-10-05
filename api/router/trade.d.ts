
export type DataElement = {
    p: number;
    s: string;
    t: number;
    v: number;
};

export type Message = {
    data : string;
    type?: string
}

type Client = {
    [key: string]: {
        webSocket: WebSocket;
        symbol: string[];
    };
};