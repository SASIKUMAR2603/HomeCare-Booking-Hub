import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly BASIC_URL = 'http://localhost:8091/api/auth';
  private readonly AUTH_HEADER = 'Authorization';

  constructor(private http: HttpClient) { }

  registerClient(signupRequestDTO: any): Observable<any> {
    return this.http.post(`${this.BASIC_URL}/client/sign-up`, signupRequestDTO);
  }

  registerCompany(signupRequestDTO: any): Observable<any> {
    return this.http.post(`${this.BASIC_URL}/company/sign-up`, signupRequestDTO);
  }

  login(username: string, password: string): Observable<HttpResponse<any>> {
    return this.http.post(`${this.BASIC_URL}/authenticate`, { username, password }, { observe: 'response'})
      .pipe(
        map((res: HttpResponse<any>) => {
          console.log(res.body);
          const tokenLength = res.headers.get(this.AUTH_HEADER)?.length;
          const bearerToken = res.headers.get(this.AUTH_HEADER)?.substring(7, tokenLength); // Fixed syntax error here
          console.log(bearerToken);
          return res;
        })
      );
  }
}