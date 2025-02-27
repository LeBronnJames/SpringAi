import request from "@/base/http/request";

export const getBgds = (params: any): Promise<any> => {
  return request({
    url: "/bgds",
    method: "get",
    params,
  });
};
