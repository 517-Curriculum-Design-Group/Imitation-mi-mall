const setSession = (key, value) => {
  window.sessionStorage.setItem(key, value);
};

const getSession = (key) => {
  return window.sessionStorage.getItem(key);
};

export const session = {
  setSession,
  getSession,
};
