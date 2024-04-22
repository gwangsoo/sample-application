/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import dayjs from 'dayjs';
import CompetitionUpdate from './competition-update.vue';
import CompetitionService from './competition.service';
import { DATE_TIME_LONG_FORMAT } from '@/shared/composables/date-format';
import AlertService from '@/shared/alert/alert.service';

import FileInfoService from '@/entities/file-info/file-info.service';
import RewardService from '@/entities/reward/reward.service';
import CountryService from '@/entities/country/country.service';
import OperatorService from '@/entities/operator/operator.service';
import EntryFeeService from '@/entities/entry-fee/entry-fee.service';

type CompetitionUpdateComponentType = InstanceType<typeof CompetitionUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const competitionSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<CompetitionUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Competition Management Update Component', () => {
    let comp: CompetitionUpdateComponentType;
    let competitionServiceStub: SinonStubbedInstance<CompetitionService>;

    beforeEach(() => {
      route = {};
      competitionServiceStub = sinon.createStubInstance<CompetitionService>(CompetitionService);
      competitionServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          competitionService: () => competitionServiceStub,
          fileInfoService: () =>
            sinon.createStubInstance<FileInfoService>(FileInfoService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          rewardService: () =>
            sinon.createStubInstance<RewardService>(RewardService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          countryService: () =>
            sinon.createStubInstance<CountryService>(CountryService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          operatorService: () =>
            sinon.createStubInstance<OperatorService>(OperatorService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          entryFeeService: () =>
            sinon.createStubInstance<EntryFeeService>(EntryFeeService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('load', () => {
      beforeEach(() => {
        const wrapper = shallowMount(CompetitionUpdate, { global: mountOptions });
        comp = wrapper.vm;
      });
      it('Should convert date from string', () => {
        // GIVEN
        const date = new Date('2019-10-15T11:42:02Z');

        // WHEN
        const convertedDate = comp.convertDateTimeFromServer(date);

        // THEN
        expect(convertedDate).toEqual(dayjs(date).format(DATE_TIME_LONG_FORMAT));
      });

      it('Should not convert date if date is not present', () => {
        expect(comp.convertDateTimeFromServer(null)).toBeNull();
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(CompetitionUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.competition = competitionSample;
        competitionServiceStub.update.resolves(competitionSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(competitionServiceStub.update.calledWith(competitionSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        competitionServiceStub.create.resolves(entity);
        const wrapper = shallowMount(CompetitionUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.competition = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(competitionServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        competitionServiceStub.find.resolves(competitionSample);
        competitionServiceStub.retrieve.resolves([competitionSample]);

        // WHEN
        route = {
          params: {
            competitionId: '' + competitionSample.id,
          },
        };
        const wrapper = shallowMount(CompetitionUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.competition).toMatchObject(competitionSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        competitionServiceStub.find.resolves(competitionSample);
        const wrapper = shallowMount(CompetitionUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
