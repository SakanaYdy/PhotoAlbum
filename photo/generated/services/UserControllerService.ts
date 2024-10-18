/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ResultUserVo } from '../models/ResultUserVo';
import type { UserDto } from '../models/UserDto';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class UserControllerService {
    /**
     * @param conform
     * @param requestBody
     * @returns ResultUserVo OK
     * @throws ApiError
     */
    public static register(
        conform: string,
        requestBody: UserDto,
    ): CancelablePromise<ResultUserVo> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/user/register',
            query: {
                'conform': conform,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @param requestBody
     * @returns ResultUserVo OK
     * @throws ApiError
     */
    public static loginUser(
        requestBody: UserDto,
    ): CancelablePromise<ResultUserVo> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/user/login',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
