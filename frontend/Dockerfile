# Stage 1: Build Angular App
FROM node:18 AS builder

WORKDIR /app

# Copy package files
COPY package*.json ./

# Install dependencies with flexible peer deps
RUN npm install --legacy-peer-deps

# Copy the rest of the app
COPY . .

# Build Angular app
RUN npm run build -- --configuration production

# Stage 2: Serve Angular App with Nginx
FROM nginx:alpine

# Automatically copy the only folder inside /app/dist
COPY --from=builder /app/dist/* /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
