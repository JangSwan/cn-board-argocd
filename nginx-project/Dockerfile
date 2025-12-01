FROM nginx:1.25.3
RUN rm /etc/nginx/conf.d/default.conf
COPY default.conf /etc/nginx/conf.d/
COPY style.css /usr/share/nginx/html/style.css
COPY bootstrap.min.css /usr/share/nginx/html/bootstrap.min.css
COPY bootstrap.min.js /usr/share/nginx/html/bootstrap.min.js
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
#CI Test
