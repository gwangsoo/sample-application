/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import EntryService from './entry.service';
import { DATE_FORMAT } from '@/shared/composables/date-format';
import { Entry } from '@/shared/model/entry.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('Entry Service', () => {
    let service: EntryService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new EntryService();
      currentDate = new Date();
      elemDefault = new Entry(
        'ABC',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'MALE',
        'NON_ATTENDANCE',
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            birthDate: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault,
        );
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find('ABC').then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find('ABC')
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a Entry', async () => {
        const returnedFromService = Object.assign(
          {
            id: 'ABC',
            birthDate: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault,
        );
        const expected = Object.assign(
          {
            birthDate: currentDate,
          },
          returnedFromService,
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Entry', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Entry', async () => {
        const returnedFromService = Object.assign(
          {
            entryNo: 'BBBBBB',
            phoenixNo: 'BBBBBB',
            cardNo: 'BBBBBB',
            name: 'BBBBBB',
            englishName: 'BBBBBB',
            rating: 1,
            mobileNo: 'BBBBBB',
            birthDate: dayjs(currentDate).format(DATE_FORMAT),
            email: 'BBBBBB',
            genderType: 'BBBBBB',
            attendanceStatusType: 'BBBBBB',
          },
          elemDefault,
        );

        const expected = Object.assign(
          {
            birthDate: currentDate,
          },
          returnedFromService,
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Entry', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Entry', async () => {
        const patchObject = Object.assign(
          {
            phoenixNo: 'BBBBBB',
            cardNo: 'BBBBBB',
            name: 'BBBBBB',
            englishName: 'BBBBBB',
            rating: 1,
            birthDate: dayjs(currentDate).format(DATE_FORMAT),
            attendanceStatusType: 'BBBBBB',
          },
          new Entry(),
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            birthDate: currentDate,
          },
          returnedFromService,
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Entry', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Entry', async () => {
        const returnedFromService = Object.assign(
          {
            entryNo: 'BBBBBB',
            phoenixNo: 'BBBBBB',
            cardNo: 'BBBBBB',
            name: 'BBBBBB',
            englishName: 'BBBBBB',
            rating: 1,
            mobileNo: 'BBBBBB',
            birthDate: dayjs(currentDate).format(DATE_FORMAT),
            email: 'BBBBBB',
            genderType: 'BBBBBB',
            attendanceStatusType: 'BBBBBB',
          },
          elemDefault,
        );
        const expected = Object.assign(
          {
            birthDate: currentDate,
          },
          returnedFromService,
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Entry', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Entry', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete('ABC').then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Entry', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete('ABC')
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
