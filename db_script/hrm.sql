CREATE TABLE "role" (
  "role_id" SERIAL PRIMARY KEY NOT NULL,
  "role_name" varchar(255),
  "status" boolean,
  "created_date" timestamp,
  "modify_date" timestamp,
  "modified_by" varchar(255),
  "modification_time" timestamp,
  "is_geust_user_allowed" boolean,
  "organization_id" int
);

CREATE TABLE "role_user_mapping" (
  "role_user_id" SERIAL PRIMARY KEY NOT NULL,
  "role_id" int4,
  "user_id" int4,
  "created_date" timestamp,
  "modify_date" timestamp,
  "status" boolean
);

CREATE TABLE "permission" (
  "permission_id" SERIAL PRIMARY KEY NOT NULL,
  "name" varchar(255),
  "description" TEXT,
  "status" boolean,
  "created_date" timestamp,
  "modify_date" timestamp
);

CREATE TABLE "role_permission_mapping" (
  "role_permission_id" SERIAL PRIMARY KEY NOT NULL,
  "permission_id" int,
  "status" boolean,
  "role_id" int,
  "created_date" timestamp,
  "modify_date" timestamp
);

CREATE TABLE "legal_entity" (
  "id" SERIAL PRIMARY KEY NOT NULL,
  "name" varchar(255) NOT NULL,
  "status" boolean,
  "email" varchar(255),
  "manager" varchar(255),
  "registration_num" int NOT NULL,
  "tax_num" int,
  "gst_number" int,
  "address" varchar(255),
  "contact_num" int,
  "created_date" timestamp,
  "modify_date" timestamp,
  "organization_id" int
);

CREATE TABLE "organization" (
  "organization_code" SERIAL PRIMARY KEY NOT NULL,
  "name" varchar(255) NOT NULL,
  "status" boolean,
  "contact_num" int,
  "size" int,
  "country" varchar(255),
  "domain" varchar(255),
  "created_date" timestamp,
  "modify_date" timestamp
);

CREATE TABLE "users" (
  "user_id" SERIAL PRIMARY KEY NOT NULL,
  "address" varchar(255),
  "created_by" varchar(255),
  "created_date" timestamp,
  "start_time" timestamp,
  "end_time" timestamp,
  "first_name" varchar(255),
  "is_guest_user" boolean NOT NULL,
  "last_name" varchar(255),
  "linked_in" varchar(255),
  "modify_by" varchar(255),
  "modify_date" timestamp,
  "phone_number" varchar(255),
  "primary_email" varchar(255),
  "status" boolean,
  "user_type" varchar(255),
  "username" varchar(255),
  "logged_in_time" timestamp,
  "password" varchar(255),
  "is_password_reset_required" boolean NOT NULL,
  "token" varchar(255),
  "job_title" varchar(255),
  "organization_id" int
);

ALTER TABLE "role" ADD FOREIGN KEY ("organization_id") REFERENCES "organization" ("organization_code");

ALTER TABLE "role_user_mapping" ADD FOREIGN KEY ("role_id") REFERENCES "role" ("role_id");

ALTER TABLE "role_user_mapping" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("user_id");

ALTER TABLE "role_permission_mapping" ADD FOREIGN KEY ("permission_id") REFERENCES "permission" ("permission_id");

ALTER TABLE "role_permission_mapping" ADD FOREIGN KEY ("role_id") REFERENCES "role" ("role_id");

ALTER TABLE "legal_entity" ADD FOREIGN KEY ("organization_id") REFERENCES "organization" ("organization_code");

ALTER TABLE "users" ADD FOREIGN KEY ("organization_id") REFERENCES "organization" ("organization_code");
