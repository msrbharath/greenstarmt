#Based on Node.js, to run Angular
FROM node:10.5

# Based on Nginx, to have only the compiled app, ready for production with Nginx
FROM nginx:1.15

COPY dist/ /usr/share/nginx/html

COPY ./nginx-custom.conf /etc/nginx/conf.d/default.conf