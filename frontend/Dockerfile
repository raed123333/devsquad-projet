# Step 1: Use an official Node.js image as a build environment
FROM node:16 AS build

# Set working directory inside container
WORKDIR /app

# Copy the package.json and package-lock.json (or yarn.lock if using Yarn)
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the Vite app files
COPY . .

# Build the Vite app for production
RUN npm run build

# Step 2: Use an Nginx server to serve the static files
FROM nginx:alpine

# Copy the build files from the build stage to Nginx's public directory
COPY --from=build /app/dist /usr/share/nginx/html

# Update Nginx configuration to serve on port 5173
RUN sed -i 's/listen       80;/listen       5173;/g' /etc/nginx/conf.d/default.conf

# Expose the port that Nginx will use
EXPOSE 5173

# Command to run Nginx and serve the Vite app
CMD ["nginx", "-g", "daemon off;"]

