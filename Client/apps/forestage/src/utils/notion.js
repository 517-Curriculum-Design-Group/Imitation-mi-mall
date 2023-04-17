import { useNotification } from "naive-ui";

const notification = useNotification();

const notify = (type = "info", content, meta, options = {}) => {
  notification[type]({
    content,
    meta,
    duration: 2500,
    keepAliveOnHover: true,
    ...options,
  });
};

export const notion = {
  notify,
};
