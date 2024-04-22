/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import CompetitionService from './competition.service';
import { DATE_TIME_FORMAT } from '@/shared/composables/date-format';
import { Competition } from '@/shared/model/competition.model';

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
  describe('Competition Service', () => {
    let service: CompetitionService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new CompetitionService();
      currentDate = new Date();
      elemDefault = new Competition('ABC', 'AAAAAAA', currentDate, currentDate, currentDate, currentDate, 'PREPARE', false, 'APP', 'AUTO');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            startDateTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            endDateTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            entryStartDateTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            entryEndDateTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
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

      it('should create a Competition', async () => {
        const returnedFromService = Object.assign(
          {
            id: 'ABC',
            startDateTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            endDateTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            entryStartDateTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            entryEndDateTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault,
        );
        const expected = Object.assign(
          {
            startDateTime: currentDate,
            endDateTime: currentDate,
            entryStartDateTime: currentDate,
            entryEndDateTime: currentDate,
          },
          returnedFromService,
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Competition', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Competition', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            startDateTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            endDateTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            entryStartDateTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            entryEndDateTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            status: 'BBBBBB',
            approval: true,
            entryApplyType: 'BBBBBB',
            entryRatingType: 'BBBBBB',
          },
          elemDefault,
        );

        const expected = Object.assign(
          {
            startDateTime: currentDate,
            endDateTime: currentDate,
            entryStartDateTime: currentDate,
            entryEndDateTime: currentDate,
          },
          returnedFromService,
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Competition', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Competition', async () => {
        const patchObject = Object.assign(
          {
            endDateTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            entryStartDateTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            entryEndDateTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            entryApplyType: 'BBBBBB',
          },
          new Competition(),
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            startDateTime: currentDate,
            endDateTime: currentDate,
            entryStartDateTime: currentDate,
            entryEndDateTime: currentDate,
          },
          returnedFromService,
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Competition', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Competition', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            startDateTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            endDateTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            entryStartDateTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            entryEndDateTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            status: 'BBBBBB',
            approval: true,
            entryApplyType: 'BBBBBB',
            entryRatingType: 'BBBBBB',
          },
          elemDefault,
        );
        const expected = Object.assign(
          {
            startDateTime: currentDate,
            endDateTime: currentDate,
            entryStartDateTime: currentDate,
            entryEndDateTime: currentDate,
          },
          returnedFromService,
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Competition', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Competition', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete('ABC').then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Competition', async () => {
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
