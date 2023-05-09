package com.hrmtool.config;

public class Constant {

    public final class Path {

        public static final String API = "/api";
        public static final String USERS = "/users";
        public static final String ORGANIZATION = "/organization";
        public static final String ROLES = "/roles";
        public static final String PERMISSION = "/permission";
        public static final String EMPLOYEE = "/employee";
        public static final String JOB = "/job";
        public static final String DEPARTMENT = "/department";

        public final class Organization {

            public static final String ORGANIZATION_CONTROLLER = API + ORGANIZATION;
            public static final String ORGANIZATION_REGISTRATION = "/registration";

            public static final String ORGANIZATION_UPDATE = "/update";

            public static final String ORGANIZATION_FETCH_BY_ORGCODE = "/get/{organizationCode}";

            public static final String ORGANIZATION_FETCH_ALL = "/getAll";

            public static final String ORGANIZATION_DELETE = "/delete/{organizationCode}";

        }

        public final class Roles {

            public static final String ROLE_CONTROLLER = API + ROLES;
            public static final String ADD_ROLE = ROLE_CONTROLLER + "/add";

            public static final String GET_ROLE = ROLE_CONTROLLER + "/get";

            public static final String DELETE_ROLE = ROLE_CONTROLLER + "/delete";

        }

        public final class Users {

            public static final String USER_CONTROLLER = API + USERS;
            public static final String ADD_USER = USER_CONTROLLER + "/add";

            public static final String GET_USERS = USER_CONTROLLER + "/get";

            public static final String DELETE_USERS = USER_CONTROLLER + "/delete";

        }

        public final class Permission {

            public static final String PERMISSION_CONTROLLER = API + PERMISSION;
            public static final String ADD_PERMISSION = PERMISSION_CONTROLLER + "/add";

            public static final String GET_PERMISSION = PERMISSION_CONTROLLER + "/get";

            public static final String DELETE_PERMISSION = PERMISSION_CONTROLLER + "/delete";

            public static final String UPDATE_PERMISSION = PERMISSION_CONTROLLER + "/update";

        }

        public final class Employee {

            public static final String EMPLOYEE_CONTROLLER = API + EMPLOYEE;
            public static final String ADD_EMPLOYEE = "/registration";

            public static final String UPDATE_EMPLOYEE = "/update/{employeeId}";

            public static final String FETCH_BY_EMPLOYEE_ID = "/get/{employeeId}";

            public static final String FETCH_ALL_EMPLOYEE = "/getAll";

            public static final String DELETE_EMPLOYEE = "/delete/{employeeId}";

        }

        public final class Job {

            public static final String JOB_CONTROLLER = API + JOB;
            public static final String ADD_JOB = "/add";

            public static final String UPDATE_JOB = "/update/{jobId}";

            public static final String FETCH_BY_JOB_ID = "/get/{jobId}";

            public static final String FETCH_ALL_JOB = "/getAll";

            public static final String DELETE_JOB = "/delete/{jobId}";

        }

        public final class Department {

            public static final String DEPARTMENT_CONTROLLER = API + DEPARTMENT;
            public static final String ADD_DEPARTMENT = "/add";

            public static final String UPDATE_DEPARTMENT = "/update/{departmentId}";

            public static final String FETCH_BY_DEPARTMENT_ID = "/get/{departmentId}";

            public static final String FETCH_ALL_DEPARTMENT = "/getAll";

            public static final String DELETE_DEPARTMENT = "/delete/{departmentId}";

        }
    }
}
