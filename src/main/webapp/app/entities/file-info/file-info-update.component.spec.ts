/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import FileInfoUpdate from './file-info-update.vue';
import FileInfoService from './file-info.service';
import AlertService from '@/shared/alert/alert.service';

type FileInfoUpdateComponentType = InstanceType<typeof FileInfoUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const fileInfoSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<FileInfoUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('FileInfo Management Update Component', () => {
    let comp: FileInfoUpdateComponentType;
    let fileInfoServiceStub: SinonStubbedInstance<FileInfoService>;

    beforeEach(() => {
      route = {};
      fileInfoServiceStub = sinon.createStubInstance<FileInfoService>(FileInfoService);
      fileInfoServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          fileInfoService: () => fileInfoServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(FileInfoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.fileInfo = fileInfoSample;
        fileInfoServiceStub.update.resolves(fileInfoSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(fileInfoServiceStub.update.calledWith(fileInfoSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        fileInfoServiceStub.create.resolves(entity);
        const wrapper = shallowMount(FileInfoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.fileInfo = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(fileInfoServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        fileInfoServiceStub.find.resolves(fileInfoSample);
        fileInfoServiceStub.retrieve.resolves([fileInfoSample]);

        // WHEN
        route = {
          params: {
            fileInfoId: '' + fileInfoSample.id,
          },
        };
        const wrapper = shallowMount(FileInfoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.fileInfo).toMatchObject(fileInfoSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        fileInfoServiceStub.find.resolves(fileInfoSample);
        const wrapper = shallowMount(FileInfoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
