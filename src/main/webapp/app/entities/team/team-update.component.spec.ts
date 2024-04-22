/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import dayjs from 'dayjs';
import TeamUpdate from './team-update.vue';
import TeamService from './team.service';
import { DATE_TIME_LONG_FORMAT } from '@/shared/composables/date-format';
import AlertService from '@/shared/alert/alert.service';

import EntryService from '@/entities/entry/entry.service';
import DivisionService from '@/entities/division/division.service';
import AffiliatedInfoService from '@/entities/affiliated-info/affiliated-info.service';
import PaymentInfoService from '@/entities/payment-info/payment-info.service';

type TeamUpdateComponentType = InstanceType<typeof TeamUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const teamSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<TeamUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Team Management Update Component', () => {
    let comp: TeamUpdateComponentType;
    let teamServiceStub: SinonStubbedInstance<TeamService>;

    beforeEach(() => {
      route = {};
      teamServiceStub = sinon.createStubInstance<TeamService>(TeamService);
      teamServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          teamService: () => teamServiceStub,
          entryService: () =>
            sinon.createStubInstance<EntryService>(EntryService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          divisionService: () =>
            sinon.createStubInstance<DivisionService>(DivisionService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          affiliatedInfoService: () =>
            sinon.createStubInstance<AffiliatedInfoService>(AffiliatedInfoService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          paymentInfoService: () =>
            sinon.createStubInstance<PaymentInfoService>(PaymentInfoService, {
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
        const wrapper = shallowMount(TeamUpdate, { global: mountOptions });
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
        const wrapper = shallowMount(TeamUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.team = teamSample;
        teamServiceStub.update.resolves(teamSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(teamServiceStub.update.calledWith(teamSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        teamServiceStub.create.resolves(entity);
        const wrapper = shallowMount(TeamUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.team = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(teamServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        teamServiceStub.find.resolves(teamSample);
        teamServiceStub.retrieve.resolves([teamSample]);

        // WHEN
        route = {
          params: {
            teamId: '' + teamSample.id,
          },
        };
        const wrapper = shallowMount(TeamUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.team).toMatchObject(teamSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        teamServiceStub.find.resolves(teamSample);
        const wrapper = shallowMount(TeamUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
